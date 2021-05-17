package net.minestom.code_generation.blocks;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.squareup.javapoet.*;
import net.minestom.code_generation.MinestomCodeGenerator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public final class BlockEntityGenerator extends MinestomCodeGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockEntityGenerator.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final File blockEntitiesFile;
    private final File outputFolder;


    public BlockEntityGenerator(@NotNull File blockEntitiesFile, @NotNull File outputFolder) {
        this.blockEntitiesFile = blockEntitiesFile;
        this.outputFolder = outputFolder;
    }

    @Override
    public void generate() {
        if (!blockEntitiesFile.exists()) {
            LOGGER.error("Failed to find block_entities.json.");
            LOGGER.error("Stopped code generation for block entities.");
            return;
        }
        if (!outputFolder.exists() && !outputFolder.mkdirs()) {
            LOGGER.error("Output folder for code generation does not exist and could not be created.");
            return;
        }
        // Important classes we use alot
        ClassName namespaceIDClassName = ClassName.get("net.minestom.server.utils", "NamespaceID");
        ClassName blockClassName = ClassName.get("net.minestom.server.instance.block", "Block");
        ClassName registryClassName = ClassName.get("net.minestom.server.registry", "Registry");

        JsonArray blockEntities;
        try {
            blockEntities = GSON.fromJson(new JsonReader(new FileReader(blockEntitiesFile)), JsonArray.class);
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to find block_entities.json.");
            LOGGER.error("Stopped code generation for block entities.");
            return;
        }
        ClassName blockEntityClassName = ClassName.get("net.minestom.server.instance.block", "BlockEntity");

        // Particle
        TypeSpec.Builder blockEntityClass = TypeSpec.classBuilder(blockEntityClassName)
                .addSuperinterface(ClassName.get("net.kyori.adventure.key", "Keyed"))
                .addModifiers(Modifier.PUBLIC).addJavadoc("AUTOGENERATED by " + getClass().getSimpleName());
        blockEntityClass.addField(
                FieldSpec.builder(namespaceIDClassName, "id")
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL).addAnnotation(NotNull.class).build()
        );
        blockEntityClass.addField(
                FieldSpec.builder(
                        ParameterizedTypeName.get(ClassName.get("java.util", "List"), blockClassName), "blocks")
                        .initializer("new $T<>()", ClassName.get("java.util", "ArrayList"))
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .addAnnotation(NotNull.class)
                        .build()
        );
        blockEntityClass.addField(
                FieldSpec.builder(
                        ParameterizedTypeName.get(ClassName.get("java.util", "List"), blockClassName), "BLOCKS")
                        .initializer("new $T<>()", ClassName.get("java.util", "ArrayList"))
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .build()
        );
        blockEntityClass.addMethod(
                MethodSpec.constructorBuilder()
                        .addParameter(ParameterSpec.builder(namespaceIDClassName, "id").addAnnotation(NotNull.class).build())
                        .addStatement("this.id = id")
                        .addModifiers(Modifier.PROTECTED)
                        .build()
        );
        // Override key method (adventure)
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("key")
                        .returns(ClassName.get("net.kyori.adventure.key", "Key"))
                        .addAnnotation(Override.class)
                        .addAnnotation(NotNull.class)
                        .addStatement("return this.id")
                        .addModifiers(Modifier.PUBLIC)
                        .build()
        );
        // getId method
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("getId")
                        .returns(namespaceIDClassName)
                        .addAnnotation(NotNull.class)
                        .addStatement("return this.id")
                        .addModifiers(Modifier.PUBLIC)
                        .build()
        );
        // addBlock method
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("addBlock")
                        .addParameter(ParameterSpec.builder(blockClassName, "block").addAnnotation(NotNull.class).build())
                        .addStatement("this.blocks.add(block)")
                        .addModifiers(Modifier.PRIVATE)
                        .build()
        );
        // getBlocks method
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("getBlocks")
                        .returns(ParameterizedTypeName.get(ClassName.get("java.util", "List"), blockClassName))
                        .addAnnotation(NotNull.class)
                        .addStatement("return this.blocks")
                        .addModifiers(Modifier.PUBLIC)
                        .build()
        );
        // toString method
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("toString")
                        .addAnnotation(NotNull.class)
                        .addAnnotation(Override.class)
                        .returns(String.class)
                        // this resolves to [Namespace]
                        .addStatement("return \"[\" + this.id + \"]\"")
                        .addModifiers(Modifier.PUBLIC)
                        .build()
        );
        // values method
        blockEntityClass.addMethod(
                MethodSpec.methodBuilder("values")
                        .addAnnotation(NotNull.class)
                        .returns(ParameterizedTypeName.get(ClassName.get(List.class), blockEntityClassName))
                        .addStatement("return $T.BLOCK_ENTITY_REGISTRY.values()", registryClassName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .build()
        );
        CodeBlock.Builder staticBlock = CodeBlock.builder();
        CodeBlock.Builder staticBlock2 = CodeBlock.builder();
        CodeBlock.Builder staticBlock3 = CodeBlock.builder();
        // Use data
        for (JsonElement e : blockEntities) {
            JsonObject blockEntity = e.getAsJsonObject();

            String blockEntityName = blockEntity.get("name").getAsString();

            blockEntityClass.addField(
                    FieldSpec.builder(
                            blockEntityClassName,
                            blockEntityName
                    ).initializer(
                            "new $T($T.from($S))",
                            blockEntityClassName,
                            namespaceIDClassName,
                            blockEntity.get("id").getAsString()
                    ).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).build()
            );
            JsonArray blocks = blockEntity.get("blocks").getAsJsonArray();
            for (JsonElement b : blocks) {
                JsonObject block = b.getAsJsonObject();
                staticBlock.addStatement(
                        "$N.addBlock($T.BLOCK_REGISTRY.get($S))",
                        blockEntityName,
                        registryClassName,
                        block.get("id").getAsString()
                );
                staticBlock3.addStatement(
                        "BLOCKS.add($T.BLOCK_REGISTRY.get($S))",
                        registryClassName,
                        block.get("id").getAsString()
                );
            }
            // Add to static init.
            staticBlock2.addStatement("$T.BLOCK_ENTITY_REGISTRY.register($N)", registryClassName, blockEntityName);
        }

        blockEntityClass.addStaticBlock(staticBlock.build());
        blockEntityClass.addStaticBlock(staticBlock2.build());
        blockEntityClass.addStaticBlock(staticBlock3.build());

        // Write files to outputFolder
        writeFiles(
                Collections.singletonList(
                        JavaFile.builder("net.minestom.server.instance.block", blockEntityClass.build())
                                .indent("    ")
                                .skipJavaLangImports(true)
                                .build()
                ),
                outputFolder
        );
    }
}
