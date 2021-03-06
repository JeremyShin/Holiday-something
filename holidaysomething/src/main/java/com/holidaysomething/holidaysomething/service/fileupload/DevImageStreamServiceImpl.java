package com.holidaysomething.holidaysomething.service.fileupload;

import com.holidaysomething.holidaysomething.domain.Product;
import com.holidaysomething.holidaysomething.domain.ProductImage;
import com.holidaysomething.holidaysomething.repository.ProductImageRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Profile("local")
@Service
@Slf4j
@RequiredArgsConstructor
public class DevImageStreamServiceImpl implements ImageStreamService {

  private final ProductImageRepository productImageRepository;

  private static String fileUploadDir = System.getProperty("user.home") + "/test/";

  @Override
  @Transactional
  public String save(MultipartFile multipartFile, Long productId) {

    Calendar cal = Calendar.getInstance();
    String path = fileUploadDir + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal
        .get(Calendar.DAY_OF_MONTH) + "/";
    File uploadDir = new File(path);
    uploadDir.mkdirs();

    String storedFileName = UUID.randomUUID().toString();
    try (
        InputStream in = multipartFile.getInputStream();
        OutputStream out = new FileOutputStream(path + storedFileName)
    ) {
      byte[] buffer = new byte[1024];
      int readCount = 0;
      while ((readCount = in.read(buffer)) != -1) {
        out.write(buffer, 0, readCount);
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe.getMessage());
    }

    ProductImage productImage = new ProductImage();
    productImage.setOriginalFileName(multipartFile.getOriginalFilename());
    productImage.setFileType(multipartFile.getContentType());
    productImage.setPath(path);
    productImage.setStoredFileName(storedFileName);
    productImage.setRegDate(LocalDateTime.now());
    productImage.setSize(multipartFile.getSize());

    if (productId != null) {
      Product product = new Product();
      product.setId(productId);
      productImage.setProduct(product);
    }

    // Category 1 = Main Image
    // Category 2 = Sub Image
    // Category 3 = Description Image
    switch (multipartFile.getName()) {
      case "mainImage":
        productImage.setCategory(1L);
        break;
      case "subImages":
        productImage.setCategory(2L);
        break;
      default:
        productImage.setCategory(3L);
        break;
    }
    productImageRepository.save(productImage);

    return productImage.getStoredFileName();
  }

  @Override
  public void readAndWrite(String saveFileName, OutputStream out) {
    FileInputStream in = null;
    int readCount = 0;
    byte[] buffer = new byte[1024];
    try {
      in = new FileInputStream(saveFileName);
      while ((readCount = in.read(buffer)) != -1) {
        out.write(buffer, 0, readCount);
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } // finally
  }
}