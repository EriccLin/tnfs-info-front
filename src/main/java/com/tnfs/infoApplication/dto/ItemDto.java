package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ItemDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("evidenceId")
    private Long evidenceId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("qualifier")
    private String qualifier;
    @JsonProperty("reserve")
    private String reserve;
}
