package uz.sud.setting.modules.category.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import uz.sud.setting.modules.category.dto.CategoryCreateDTO;
import uz.sud.setting.modules.category.dto.CategoryResponseDTO;
import uz.sud.setting.modules.category.dto.CategorySearchDTO;
import uz.sud.setting.modules.category.dto.CategoryUpdateDTO;
import uz.sud.setting.modules.category.service.CategoryService;


@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    public CategoryResponseDTO create(@Valid CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }

    @PUT
    @Path("/{id}")
    public CategoryResponseDTO update(
            @PathParam("id") Long id,
            @Valid CategoryUpdateDTO dto
    ) {
        return categoryService.update(id, dto);
    }

    @GET
    @Path("/{id}")
    public CategoryResponseDTO getById(@PathParam("id") Long id) {
        return categoryService.getById(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }

    @GET
    public List<CategoryResponseDTO> search(
            @QueryParam("title") String title,
            @QueryParam("isActive") Boolean isActive,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        CategorySearchDTO dto = new CategorySearchDTO();
        dto.title = title;
        dto.isActive = isActive;
        dto.page = page;
        dto.size = size;

        return categoryService.search(dto);
    }
}
