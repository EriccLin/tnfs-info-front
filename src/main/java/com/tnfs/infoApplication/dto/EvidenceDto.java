package com.tnfs.infoApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EvidenceDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("criminalCaseId")
    private Long criminalCaseId;
    @JsonProperty("items")
    private List<ItemDto> items;
    @JsonProperty("stime")
    private LocalDateTime stime;
    @JsonProperty("location")
    private String location;
    @JsonProperty("type")
    private String type;
    @JsonProperty("code")
    private String code;
    @JsonProperty("isSTD")
    private Boolean isSTD;
    @JsonProperty("reserve")
    private String reserve;
    @JsonProperty("collectionSheets")
    private Set<CollectionSheetDto> collectionSheets;
    @JsonProperty("examinations")
    private Set<ExaminationDto> examinations;
}
