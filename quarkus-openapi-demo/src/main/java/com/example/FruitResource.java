package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Schema(description = "Fruta catalogada")
    public record Fruit(
            @Schema(description = "Identificador", example = "1") long id,
            @Schema(description = "Nombre visible", example = "Manzana") String name) {
    }

    private static final List<Fruit> ALL = List.of(
            new Fruit(1L, "Manzana"),
            new Fruit(2L, "Pera"),
            new Fruit(3L, "Ciruela"));

    @GET
    @Operation(summary = "Listar frutas")
    @APIResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Fruit.class)))
    public List<Fruit> list() {
        return ALL;
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener una fruta por id")
    @APIResponse(
            responseCode = "200",
            description = "Encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = Fruit.class)))
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Fruit get(
            @PathParam("id")
            @Parameter(description = "ID de la fruta", required = true, example = "1")
            long id) {
        return ALL.stream()
                .filter(f -> f.id() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
