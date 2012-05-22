package network;

import java.io.Serializable;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class SessionInformation implements Serializable {

    private final int id;
    private final String nickname;

    public SessionInformation(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SessionInformation{" + "id=" + id + ", nickname=" + nickname + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SessionInformation)) {
            return false; // different class
        }
        SessionInformation other = (SessionInformation) o;
        return nickname.equals(other.nickname) && id == other.id;
    }
}
