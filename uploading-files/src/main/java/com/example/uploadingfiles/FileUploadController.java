package com.example.uploadingfiles;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This example shows how the JSF-alike/templates work in Spring MVC. The
 * returned Strings in these methods are more or less like commands. The strings
 * might be the name of a view, such that the template knows what to update or
 * display. The strings can also represent certain actions, such as redirection.
 */
@Controller
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        Stream<Path> allPath = storageService.loadAll();

        // update the model, which is then reflected on view
        model.addAttribute("files",
                allPath.map(path -> MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build()
                        .toUri().toString()) // creates a collection of links for files
                        .collect(Collectors.toList())); // convert this structure to a list

        // return the name of the template (uploadForm.html)
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", file.getFilename())).body(file);
    }

    // MultiplarFile is for IO, RedirectAttributes is a Model, it's only
    // used when a redirectView name is returned
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                String.format("You successfully uploaded %s !", file.getOriginalFilename()));
        return "redirect:/"; // this tells the view to redirect back to '/'
    }

    @ExceptionHandler(StorageFileNotFoundException.class) // handle this exception in this way
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}