package com.techradge.fabler.di.component;

import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.di.module.ActivityModule;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.login.LoginActivity;
import com.techradge.fabler.ui.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(LoginActivity activity);

    void inject(ComposeActivity activity);
}
