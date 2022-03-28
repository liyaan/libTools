package com.liyaan.myffmepgfirst.paging.pag

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.liyaan.myffmepgfirst.paging.model.ListDataX
import com.liyaan.myffmepgfirst.paging.model.ListEntity
import com.liyaan.myffmepgfirst.paging.net.MovieApi
import com.liyaan.myffmepgfirst.paging.net.RetrofitClient

class ListPagingSource:PagingSource<Int,ListDataX>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListDataX> {
        val currentPage = params.key?:1
        val entity = RetrofitClient.createApi(MovieApi::class.java).list(currentPage)
        var prevKey:Int? = null
        var nextKey:Int? = null
        val realPageSize = 10
        val initialLoadSize = 20
        if (currentPage == 1){
            prevKey = null
            nextKey = initialLoadSize/realPageSize+1
        }else{
            prevKey = currentPage - 1
            nextKey = if (entity.data.curPage<entity.data.total) currentPage+1 else null
        }
        return try {
            LoadResult.Page(data = entity.data.datas,
                prevKey = prevKey,
                nextKey=nextKey)
        }catch (e:Exception){
            e.printStackTrace()
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ListDataX>): Int? {
        return state.anchorPosition
    }
}