package sample;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.ServiceObjectSupplier;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportInDescription;
import org.apache.axis2.engine.AxisServer;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import sample.pojo.data.Weather;
import sample.pojo.service.WeatherService;
import sample.pojo.service.WeatherServiceSupplier;

import javax.xml.namespace.QName;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 06.05.13 07:50
 */
@RunWith(MockitoJUnitRunner.class)
public class AxisServerAndClientTest implements ServiceObjectSupplier
{

    @InjectMocks
    private static WeatherService sut = new WeatherService();

    @Mock
    private static WeatherServiceSupplier supplier;


    private static int port = RemoteUtil.findFreePort();
    private static AxisServer server = new AxisServer();


    @BeforeClass
    public static void beforeClass() throws Exception
    {

        setupAxisServer(WeatherService.class.getName(), AxisServerAndClientTest.class.getName());

    }

    @AfterClass
    public static void afterClass() throws Exception
    {
        server.stop();
    }


    @Test
    public void getWeather() throws Exception
    {
        // Setup Mock
        when(supplier.getWeather(anyString())).thenAnswer(new Answer<Weather>()
        {
            @Override
            public Weather answer(InvocationOnMock invocation) throws Throwable
            {

                return new Weather(42, "It'll be sunny in " + invocation.getArguments()[0], false, 0);
            }
        });

        // Axis 2 Client stuff
        RPCServiceClient serviceClient = new RPCServiceClient();

        Options options = serviceClient.getOptions();

        EndpointReference targetEPR = new EndpointReference("http://localhost:" + port + "/axis2/services/WeatherService");
        options.setTo(targetEPR);

        // Getting the weather
        QName opGetWeather = new QName("http://service.pojo.sample", "getWeather");
        Object[] opGetWeatherArgs = new Object[] { "Weinheim" };
        Class[] returnTypes = new Class[] { Weather.class };

        Object[] response = serviceClient.invokeBlocking(opGetWeather, opGetWeatherArgs, returnTypes);

        Weather weather = (Weather) response[0];

        // Assertions
        assertEquals("It'll be sunny in Weinheim", weather.getForecast());
    }

    /**
     * Setup and start Axis-Server.
     *
     * @param serviceClassName
     * @param mockSupplierClassName
     * @throws AxisFault
     */
    private static void setupAxisServer(String serviceClassName, String mockSupplierClassName) throws AxisFault
    {
        Map<String, TransportInDescription> transports =
                server.getConfigurationContext().getAxisConfiguration().getTransportsIn();

        for (Map.Entry<String, TransportInDescription> objectEntry : transports.entrySet())
        {
            TransportInDescription desc = objectEntry.getValue();
            Parameter param = desc.getParameter("port");
            param.setValue("" + port);
        }

        server.getConfigurationContext().getAxisConfiguration()
                .addParameter(Constants.SERVICE_OBJECT_SUPPLIER, mockSupplierClassName);
        server.deployService(serviceClassName);
    }


    /**
     * Callback-Method to retrieve the Service
     *
     * @param theService
     * @return Object
     */
    public Object getServiceObject(AxisService theService)
    {
        String service = theService.getName();

        if (service.equals("WeatherService"))
        {
            return sut;
        }
        return null;
    }
}
