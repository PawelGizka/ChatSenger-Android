package com.pgizka.gsenger.dagger2;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.pgizka.gsenger.conversationView.ConversationContract;
import com.pgizka.gsenger.conversationView.ConversationPresenter;
import com.pgizka.gsenger.jobqueue.BaseJob;
import com.pgizka.gsenger.mainView.chats.ChatsContract;
import com.pgizka.gsenger.mainView.chats.ChatsPresenter;
import com.pgizka.gsenger.mainView.friends.ContactsContract;
import com.pgizka.gsenger.mainView.friends.ContactsPresenter;
import com.pgizka.gsenger.provider.Repository;
import com.pgizka.gsenger.util.ContactsUtil;
import com.pgizka.gsenger.util.UserAccountManager;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final GSengerApplication application;

    public ApplicationModule(GSengerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public Repository providesRepository() {
        return new Repository(application);
    }

    @Provides
    @Singleton
    public JobManager providesJobManager() {
        Configuration config = new Configuration.Builder(application)
                .consumerKeepAlive(45)
                .maxConsumerCount(3)
                .minConsumerCount(1)
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if (job instanceof BaseJob) {
                            ((BaseJob) job).inject(application.getApplicationComponent());
                        }
                    }
                })
                .build();
        return new JobManager(application, config);
    }

    @Provides
    @Singleton
    public UserAccountManager providesUserAccountManager() {
        return new UserAccountManager(application);
    }

    @Provides
    @Singleton
    public ContactsUtil providesContactsUtil() {
        return new ContactsUtil(application);
    }

    @Provides
    @Singleton
    public ContactsContract.Presenter providesFriendsPresenter() {
        return new ContactsPresenter();
    }

    @Provides
    @Singleton
    public ChatsContract.Presenter providesChatsPresenter() {
        return new ChatsPresenter();
    }

    @Provides
    @Singleton
    public ConversationContract.Presenter providesConversationPresenter() {
        return new ConversationPresenter();
    }

}
