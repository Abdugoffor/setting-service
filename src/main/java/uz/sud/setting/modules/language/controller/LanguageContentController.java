package uz.sud.setting.modules.language.controller;

import java.util.List;
import java.util.Map;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import uz.sud.setting.modules.language.dto.LanguageContentDto;
import uz.sud.setting.modules.language.service.LanguageContentService;

@Path("/v1/language-content")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LanguageContentController {
    private final LanguageContentService service;

    public LanguageContentController(LanguageContentService service) {
        this.service = service;
    }

    @PUT
    public List<LanguageContentDto.Response> upsert(LanguageContentDto.Update request) {
        return service.upsertMultiple(request);
    }

    @GET
    @Path("/by-key/{key}")
    public LanguageContentDto.ByKeyResponse getByKey(@PathParam("key") String key) {
        return service.getByKey(key);
    }

    @GET
    @Path("/page/{languageId}")
    public List<LanguageContentDto.Response> getPageByLanguageId(
            @PathParam("languageId") Long languageId,
            @QueryParam("key") String key,
            @QueryParam("value") String value,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size) {
        return service.getPageByLanguageId(languageId, key, value, page, size);
    }

    @DELETE
    @Path("/{key}")
    public void deleteByKey(@PathParam("key") String key) {
        service.deleteByKey(key);
    }

    @GET
    @Path("/{language_id}")
    public Map<String, String> getByLanguageId(@PathParam("language_id") Long languageId) {
        return service.getByLanguageId(languageId);
    }
}
