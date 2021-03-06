package com.holidaysomething.holidaysomething.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ProductOrderDetailDto {
  @NotNull
  private Long productId;

  @NotNull
  private String manufacturer;

  @NotNull
  private String productName;

  @NotNull
  private String productImage;

  @NotNull
  private Integer originalPrice;

  @NotNull
  private Integer sellingPrice;

  private Integer mileage;

  private Integer shippingPrice;

  private String img;

  private Long optionId;

  private String optionName;

  private Integer optionPrice;

  @NotNull
  private  Integer quantity;
}
