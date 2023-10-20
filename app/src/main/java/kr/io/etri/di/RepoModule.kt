package kr.io.etri.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.io.etri.data.repositoryimpl.LegalQARepositoryImpl
import kr.io.etri.domain.repository.LegalQARepository
import javax.inject.Singleton

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:51
 */
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    /**
     *  @author Naedong
     *
     *  @annotation @Singleton
     *  @explanation Injector를 통해 단 한번 인스턴스화 시키기 위함
     *
     *  @annotation @Provides
     *  @explanation 종속 항목을 정의하기 위한 annotation
     *
     *  @fun provideRepository
     *  @explanation Repository 종속성을 주입
     *
     *  @param repositoryImpl : RepositoryImpl
     *  @explanation repositoryImpl 정보
     *
     *
     *  @return Repository
     *  @explanation repositoryImpl을 Repository에 주입
     */


    @Singleton
    @Provides
    fun provideRepository(
        repositoryImpl : LegalQARepositoryImpl
    ) : LegalQARepository = repositoryImpl


}