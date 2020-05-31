package com.example.uploadingfiles;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * StorageService for file system, all files are resolved/stored at level 1
 * (depth)
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private static final Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);

    public FileSystemStorageService(StoragetProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void store(MultipartFile file) {
        String fname = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty())
                throw new StorageException(String.format("Failed to store empty file: '%s'", fname));

            if (fname.contains(".."))
                throw new StorageException(
                        String.format("Cannot store file with relative path outside current directory: '%s'", fname));

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, rootLocation.resolve(fname), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException(String.format("Failed to store file: '%s'", fname));
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation)).map(rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path fpath = load(filename);
            Resource res = new UrlResource(fpath.toUri());
            if (res.exists() || res.isReadable())
                return res;
            else
                throw new StorageFileNotFoundException(filename);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new StorageFileNotFoundException(filename);
        }
    }

    @Override
    public void deleteAll() {
        try {
            // there is one for File, but that slients the Exception thrown and only return
            // false this one might be better
            FileSystemUtils.deleteRecursively(rootLocation);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}