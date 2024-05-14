package quarkus;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import quarkus.model.Account;

@Path("/accounts")
public class AccountResource {
	
	@Provider                                                                 
	  public static class ErrorMapper implements ExceptionMapper<Exception> {   
	 
	    @Override
	    public Response toResponse(Exception exception) {                       
	 
	      int code = 500;
	      if (exception instanceof WebApplicationException) {                   
	         ((WebApplicationException) exception).getResponse().getStatus();
	      }
	 
	      JsonObjectBuilder entityBuilder = Json.createObjectBuilder()          
	          .add("exceptionType", exception.getClass().getName())
	          .add("code", code);
	 
	      if (exception.getMessage() != null) {                                 
	        entityBuilder.add("error", exception.getMessage());
	      }
	 
	      return Response.status(code)                                          
	          .entity(entityBuilder.build())
	          .build();
	    }
	  }

	Set<Account> accounts = new HashSet<>();

	@PostConstruct
	public void setup() {
		accounts.add(new Account(123456789L, 987654321L, "George Baird", new BigDecimal("354.23")));
		accounts.add(new Account(121212121L, 888777666L, "Mary Taylor", new BigDecimal("560.03")));
		accounts.add(new Account(545454545L, 222444999L, "Diana Rigg", new BigDecimal("422.00")));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Account> allAccount() {
		return accounts;
	}
	
	@GET
	@Path("/{accountNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("accountNumber") Long accountNumber) {
		Optional<Account> response = accounts.stream()
				.filter(acct -> acct.getAccountNumber().equals(accountNumber))
				.findFirst();
		
		return response.orElseThrow(() 
				-> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));
	}

}
