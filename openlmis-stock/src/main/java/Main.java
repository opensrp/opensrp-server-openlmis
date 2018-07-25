import org.opensrp.stock.openlmis.repository.OrderableRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

    public static void main(String[] args){
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("//Users/vkaruri/Desktop/Ona_Projects/opensrp-server-openlmis/openlmis-stock/src/test/resources/test-openlmis-application-context.xml");
        OrderableRepository repository = context.getBean(OrderableRepository.class);
        repository.getAll();
    }
}
