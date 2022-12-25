package com.tyin.server.components;


import com.tyin.core.components.PropertiesComponents;
import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.UploadTmpRes;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.tyin.core.constants.PropertiesKeyConstants.*;
import static com.tyin.core.constants.ResMessageConstants.UPLOAD_FAILED;

/**
 * @author Tyin
 * @date 2022/5/11 2:08
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class UploadComponents {
    private final PropertiesComponents propertiesComponents;

    public UploadTmpRes uploadTmp(MultipartFile file, AuthAdminUser user) {
        return uploadTmp(file);
    }

    public UploadTmpRes uploadPackageTmp(MultipartFile updatePackage, AuthAdminUser user) {
        return uploadTmp(updatePackage);
    }

    private UploadTmpRes uploadTmp(MultipartFile file) {
        String ossUrl = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_HOST);
        String ossServer = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_SERVER_URI);
        String ossTmp = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_URI_TEMP);

        String originalFilename = file.getOriginalFilename();
        String suffix = Objects.isNull(originalFilename) ? "" : originalFilename.split("\\.")[1];
        String fileName = System.currentTimeMillis() + "-" + StringUtils.getUuid() + "." + suffix;
        String serverTmpPath = ossServer + ossTmp;
        String absolutePath;
        String md5 = "";
        try {
            md5 = DigestUtils.md5Hex(file.getBytes());
            File abFile = new File(serverTmpPath);
            Path path = abFile.toPath();
            Files.createDirectories(path);
            File newFile = new File(serverTmpPath, fileName);
            absolutePath = newFile.getAbsolutePath();
            newFile = new File(absolutePath);
            file.transferTo(newFile);
        } catch (IOException e) {
            Asserts.fail(UPLOAD_FAILED);
        }
        return UploadTmpRes.builder()
                .src(ossUrl + ossTmp + fileName)
                .uri(ossTmp)
                .fileName(fileName)
                .md5(md5)
                .build();
    }
}
