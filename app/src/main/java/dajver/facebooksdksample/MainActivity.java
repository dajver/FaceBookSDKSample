package dajver.facebooksdksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainActivity extends Activity {

    //переменная которую мы будем использовать для авторизации
    private UiLifecycleHelper uiHelper;
    //наша кнопка для авторизации
    private LoginButton enterByFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //обязательно почему то нужно это вписывать между super и
        // setContentView, непонятно но факт, нужно делать именно так
        // statusCallback наша сессия которую мы запоминаем для дальнейшей работы
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.textView);
        //наша кнопка  по которой будем логиниться
        enterByFB = (LoginButton) findViewById(R.id.fb_login_button);
        enterByFB.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    //получаема имя и выводим в текст вью
                    text.setText(user.getName());
                }
            }
        });
    }

    // наша сессия она обрабатывает здесь возврат из диалога который появляется
    // так же обрабатывает событие если приложение facebook установлено
    // и сдк обращается к нему и получает данные, в общем все происходит здесь
    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
                //здесь так же можно получить токен вот таким способом
                //session.getAccessToken();
                // и кучу всего еще можно получить из сесии
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };

    //как только возвращаемся обратно запускаем обратно поток
    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    // на паузе стопим все к чертям
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    //выходя гасите свет, убиваем процес что бы не палить електричество
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    //по возвращению обратно на активность передаем все полученные данные с диалога в колбек
    // и живем дальше счастливо
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    //при вращении экрана и т д сохраняем все что происходит на экране,
    // а то активити обычно обновляется. а диалог останется жив
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }
}
