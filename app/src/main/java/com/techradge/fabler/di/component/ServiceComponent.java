package com.techradge.fabler.di.component;

import com.techradge.fabler.di.PerService;
import com.techradge.fabler.di.module.ServiceModule;
import com.techradge.fabler.service.SyncService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}