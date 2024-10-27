package personal.bookshelf.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import personal.bookshelf.core.exception.EntityAlreadyExistsException;
import personal.bookshelf.core.exception.EntityInvalidArgumentsException;
import personal.bookshelf.core.exception.EntityNotFoundException;
import personal.bookshelf.core.util.validator.ValidatorUtil;
import personal.bookshelf.dto.BookInsertDTO;
import personal.bookshelf.dto.BookReadOnlyDTO;
import personal.bookshelf.dto.BookUpdateDTO;
import personal.bookshelf.service.IBookService;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/books")
public class BookRestController {

    private final IBookService bookService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(BookInsertDTO bookInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentsException {

        // Validation
        List<String> errors = ValidatorUtil.validateDTO(bookInsertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentsException("Book", String.join(", ", errors));
        }

        BookReadOnlyDTO bookReadOnlyDTO = bookService.insertBook(bookInsertDTO);

        return Response.status(Response.Status.CREATED)
                       .entity(bookReadOnlyDTO)
                       .build();
    }

    @DELETE
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("bookId") Long id) throws EntityNotFoundException {
        BookReadOnlyDTO bookReadOnlyDTO = bookService.getBookById(id);
        bookService.deleteBook(id);
        return Response.status(Response.Status.OK).entity(bookReadOnlyDTO).build();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(BookUpdateDTO bookUpdateDTO) throws EntityNotFoundException, EntityInvalidArgumentsException{

        // Validation
        List<String> errors = ValidatorUtil.validateDTO(bookUpdateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentsException("Book", String.join(", ", errors));
        }

        BookReadOnlyDTO bookReadOnlyDTO = bookService.updateBook(bookUpdateDTO);
        return Response.status(Response.Status.OK).entity(bookReadOnlyDTO).build();
    }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("bookId") Long id) throws EntityNotFoundException {
        BookReadOnlyDTO bookReadOnlyDTO = bookService.getBookById(id);
        return Response.status(Response.Status.OK).entity(bookReadOnlyDTO).build();
    }

//    @GET
//    @Path("/{bookTitle}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBookByTitle(@PathParam("bookTitle") String bookTitle) throws EntityNotFoundException {
//        BookReadOnlyDTO bookReadOnlyDTO = bookService.getBookByTitle(bookTitle);
//        return Response.status(Response.Status.OK).entity(bookReadOnlyDTO).build();
//    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        List<BookReadOnlyDTO> allBooks = bookService.getAllBooks();
        return Response.status(Response.Status.OK).entity(allBooks).build();
    }
}
