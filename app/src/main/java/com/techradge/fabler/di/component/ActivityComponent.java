package com.techradge.fabler.di.component;

import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.di.module.ActivityModule;
import com.techradge.fabler.ui.feedback.FeedbackActivity;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.draft.DraftFragment;
import com.techradge.fabler.ui.home.HomeFragment;
import com.techradge.fabler.ui.login.LoginActivity;
import com.techradge.fabler.ui.main.MainActivity;
import com.techradge.fabler.ui.read.ReadActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(ComposeActivity activity);

    void inject(FeedbackActivity activity);

    void inject(ReadActivity activity);

    void inject(HomeFragment fragment);

    void inject(DraftFragment fragment);
}
