package testsabai.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
/*@RunWith(SpringRunner.class)
@WebMvcTest(value=MainController.class, secure=false)
public class TransactionTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean 
	private TransactionService ts;

	private Transaction tranObj = new Transaction();

	@Test
	public void getAllTransaction() throws Exception{
		
	Iterable<Transaction> tranList =  ts.findAll();
	
	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri)
			//.post("/students/Student1/courses")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON);
	
	MvcResult result = mockMvc.perform(requestB
			uilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	assertEquals("http://localhost/students/Student1/courses/1",
			response.getHeader(HttpHeaders.LOCATION));

	
	}
}
*/


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

	@Test
	public void contextLoads() {
	}

}