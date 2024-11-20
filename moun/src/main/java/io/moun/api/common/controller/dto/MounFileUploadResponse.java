package io.moun.api.common.controller.dto;

import lombok.Data;

@Data
public class MounFileUploadResponse {
    private Long songId;
    private String songFileName;
    private Long coverImgId;
    private String coverImgFileName;

    public MounFileUploadResponse(Long songId, String songFileName, Long coverImgId, String coverImgFileName) {
        this.songId = songId;
        this.songFileName = songFileName;
        this.coverImgId = coverImgId;
        this.coverImgFileName = coverImgFileName;
    }
}
