import static org.junit.Assert.assertEquals;

import com.almundo.dao.DispatcherDao;
import com.almundo.dao.InitDao;
import com.almundo.dao.impl.DispatcherDaoImpl;
import com.almundo.dao.impl.InitDaoImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-dispatcher-servlet.xml")
@WebAppConfiguration
public class DispatchCallTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private InitDao initDao;

    @Autowired
    private DispatcherDao dispatcherDao;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testDispatchCall(){

        try {

            initDao.loadData();
            for (int i=0; i<10; i++){
                dispatcherDao.dispatchCall();
            }

            int cantidadAtentidos = dispatcherDao.cierreExecutorService();

            assertEquals(cantidadAtentidos, 10);

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
