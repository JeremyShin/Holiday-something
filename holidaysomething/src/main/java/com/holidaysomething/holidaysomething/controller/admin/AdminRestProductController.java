package com.holidaysomething.holidaysomething.controller.admin;

import com.holidaysomething.holidaysomething.domain.ProductCategory;
import com.holidaysomething.holidaysomething.service.admin.AdminProductRegisterService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product")
public class AdminRestProductController {

  private AdminProductRegisterService adminProductService;
  private static final Log log = LogFactory.getLog(AdminRestProductController.class);

  public AdminRestProductController(AdminProductRegisterService adminProductService) {
    this.adminProductService = adminProductService;
  }

  @GetMapping("/subCategory/{largerId}")
  public List<ProductCategory> getSubCategories(@PathVariable("largerId") Long largerId) {
    log.info("========================================================");
    log.info("getSubCategories 진입, largerId: " + largerId);
    List<ProductCategory> categories = adminProductService.productCategoryList(largerId);
    log.info("========================================================");

    return categories;
  }
}
