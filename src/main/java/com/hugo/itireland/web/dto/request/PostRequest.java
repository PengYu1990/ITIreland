package com.hugo.itireland.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private Long userId;
    private String title;

    @JsonProperty("contentJson")
    private JsonNode contentNode;
    private List<String> tags;
    private String category;

    public Long getUserId() {
        return userId;
    }

}
