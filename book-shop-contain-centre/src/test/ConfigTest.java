import com.contain.centre.ContainCentreApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @author novLi
 * @date 2020年01月17日 14:09
 */
@Slf4j
@RefreshScope
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ContainCentreApplication.class})
public class ConfigTest {

    @Value("${test}")
    private String value;

    @Test
    public void Test() {
        log.info(value);
    }


}
