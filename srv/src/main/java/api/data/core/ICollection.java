package api.data.core;

import java.util.Date;

public interface ICollection {
    String getKey();
    Date getCreated();
    Date getLastUpdated();
}
