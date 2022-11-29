package dreamjob.store;

import dreamjob.Main;
import dreamjob.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UserDBStoreTest {

    @Test
    public void whenSuccessfullAddNewUser() {
        UserDBStore store = new UserDBStore(new Main().loadPool());
        User user = new User(1, "Andrew", "1", LocalDateTime.now());
        store.add(user);
        Optional<User> userInDB = store.findById(user.getId());
        assertThat(userInDB.get(), is(user));
    }
}