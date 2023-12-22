package com.example.new_online_libary_project.book;

import com.example.new_online_libary_project.book.dto.BookRegisterDto;
import com.example.new_online_libary_project.book.entity.Book;
import com.example.new_online_libary_project.entity.MyBookList;
import com.example.new_online_libary_project.service.MyBookService;
import com.example.new_online_libary_project.upload.UploadService;
import com.example.new_online_libary_project.upload.entity.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final MyBookService myBookService;
    private final UploadService uploadService;
    private static final Path rootPathFile = Path.of( "src/main/resources/static/files/");
    private static final Path rootPathCover = Path.of( "src/main/resources/static/images/");

    @GetMapping("/books")
    public ModelAndView getAll(){
        List<Book>bookList = bookService.getAllBook();
        // ModelAndView modelAndView = new ModelAndView();
        // modelAndView.setViewName("bookList");
        // modelAndView.addObject("book",bookList);
        return new ModelAndView("/book/bookList","books",bookList);

    }
    @GetMapping("/book_register")
    public String bookRegister(){
        return "admin/bookRegister";
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute BookRegisterDto bookRegisterDto) throws IOException {
        MultipartFile file = bookRegisterDto.getFile();
        MultipartFile cover = bookRegisterDto.getCover();
        String originalNameFile = bookRegisterDto.getFile().getOriginalFilename();
        String originalNameCover = bookRegisterDto.getCover().getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalNameFile);
        String coverName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalNameCover);
        Path pathFile = Path.of("src/main/resources/static/files/", fileName);
        Path pathCover = Path.of("src/main/resources/static/images/", fileName);
        file.transferTo(pathFile);
        cover.transferTo(pathCover);
        String extension1 = StringUtils.getFilenameExtension(originalNameFile);
        String extension2 = StringUtils.getFilenameExtension(originalNameCover);

        Upload uploadFile = Upload.builder()
                .generatedName(fileName)
                .originalName(originalNameFile)
                .extension(extension1)
                .size(file.getSize())
                .mimeType(file.getContentType())
                .build();
        uploadService.save(uploadFile);
        Upload uploadCover = Upload.builder()
                .generatedName(coverName)
                .originalName(originalNameCover)
                .extension(extension2)
                .size(cover.getSize())
                .mimeType(cover.getContentType())
                .build();
        uploadService.save(uploadCover);

        Book book = Book.builder()
                .title(bookRegisterDto.getTitle())
                .description(bookRegisterDto.getDescription())
                .author(bookRegisterDto.getAuthor())
                .language(bookRegisterDto.getLanguage())
                .publisher(bookRegisterDto.getPublisher())
                .year(bookRegisterDto.getYear())
                .pages(bookRegisterDto.getPages())
                .cover(uploadCover)
                .file(uploadFile)
                .createAd(LocalDateTime.now())
                .updateAd(LocalDateTime.now())
                .build();
        bookService.create(book);
        Path pathToUploadFile = rootPathFile.resolve(uploadFile.getGeneratedName());
        Path pathToUploadCover = rootPathCover.resolve(uploadCover.getGeneratedName());
        Files.copy(file.getInputStream(), pathToUploadFile, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(file.getInputStream(), pathToUploadCover, StandardCopyOption.REPLACE_EXISTING);

        return "redirect:/book/books";
    }

    @GetMapping("/detail/{id}")
    public String detailsBook(@PathVariable("id")Integer id,Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return ("book/book_details");
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Path filePath = rootPathFile.resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

/*  @GetMapping("/storage/show/{filename}")
    public String fileStorageShow(@PathVariable String filename, HttpServletResponse response) throws IOException {
        Path path = rootPath.resolve(filename);
        byte[] bytes = Files.readAllBytes(path);
        response.getOutputStream().write(bytes);
        return "book/book_details";
    }*/

   /* @GetMapping("/file/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        FileSystemResource fileSystemResource = new FileSystemResource("src/main/resources/static/images/"+filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition","attachment;filename = "+filename)
                .body(fileSystemResource);
    }*/

    @GetMapping("/myList/{id}")
    public String getMyList(@PathVariable("id") Integer id){
       Book book = bookService.getBookById(id);
        MyBookList myBook = new MyBookList(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getAuthor(),
                book.getLanguage(),
                book.getPublisher(),
                book.getYear(),
                book.getPages(),
                book.getCover(),
                book.getFile(),
                book.getCreateAd(),
                book.getUpdateAd());
       myBookService.createMyBook(myBook);
        return "redirect:/book/my_books";
    }
    @GetMapping("/my_books")
    public String getAllMyBookList(Model model){
        List<MyBookList>myBookList = myBookService.getAllMyBookList();
        model.addAttribute("bookList",myBookList);
        return "book/myBooks";

    }
    @GetMapping("/update/{id}")
    public String updateBook(@PathVariable("id") Integer id,Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book",book);
        return "admin/updateBook";
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id){
       bookService.deleteById(id);
        return "redirect:/book/books";
    }
}

