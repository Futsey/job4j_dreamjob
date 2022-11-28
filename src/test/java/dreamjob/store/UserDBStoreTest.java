package dreamjob.store;

import dreamjob.Main;
import dreamjob.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UserDBStoreTest {
    /**TODO userInDb всегда null. Исправить
     *
    @Test
    public void whenSuccessfullAddNewUserAndEmailsEqual() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        User user = new User(1, "Andrew", "1", LocalDateTime.now());
        store.add(user);
        store.add(user);
        User userInDb = store.findById(user.getId());
        assertThat(userInDb.getEmail(), is(user.getEmail()));
    }
     */
}