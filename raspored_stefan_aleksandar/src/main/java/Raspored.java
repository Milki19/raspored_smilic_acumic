import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Raspored {

    protected String filename;
    protected List<String> izuzetiDani;

    public Raspored(String filename) {
        this.filename = filename;
    }

    public abstract void loadRaspored(String filename);

    public void save(List<Objects> objectsList) {


    }

    public abstract Object findBy ();

    public abstract Date poslenjiPutPromenjen ();






}
