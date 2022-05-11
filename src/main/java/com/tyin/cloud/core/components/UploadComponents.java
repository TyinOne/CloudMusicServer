package com.tyin.cloud.core.components;

import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.model.res.UploadTmpRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/5/11 2:08
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class UploadComponents {
    private final PropertiesComponents propertiesComponents;

    public UploadTmpRes upload(MultipartFile file, AuthAdminUser user) {
        String originalFilename = file.getOriginalFilename();
        originalFilename = Objects.isNull(originalFilename) ? "file.jpeg" : originalFilename;
        String suffix = originalFilename.split("\\.")[1];
        String fileName = user.getAccount() + "-" + System.currentTimeMillis() + "-" + StringUtils.getUuid() + "." + suffix;
        String serverTmpPath = propertiesComponents.getOssServer() + propertiesComponents.getOssTmp();
        String absolutePath;
        try {
            File abFile = new File(serverTmpPath);
            Path path = abFile.toPath();
            Files.createDirectories(path);
            File newFile = new File(serverTmpPath, fileName);
            absolutePath = newFile.getAbsolutePath();
            newFile = new File(absolutePath);
            file.transferTo(newFile);
        } catch (IOException e) {
            Asserts.fail("上传文件失败:" + e.getMessage());
        }
        return UploadTmpRes.builder()
                .src(propertiesComponents.getOssUrl() + propertiesComponents.getOssTmp() + fileName)
                .uri(propertiesComponents.getOssTmp())
                .fileName(fileName)
                .build();
    }
}
