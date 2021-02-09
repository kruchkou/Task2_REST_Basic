package com.epam.esm.service.model.dto;

import com.epam.esm.service.model.dto.GiftCertificateDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class UserOrderDto {

    private Integer id;
    private List<GiftCertificateDto> gifts;
    private Integer price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;

}
