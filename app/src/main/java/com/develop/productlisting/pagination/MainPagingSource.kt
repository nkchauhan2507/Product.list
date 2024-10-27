package com.develop.productlisting.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.develop.productlisting.data.Product
import com.develop.productlisting.database.IProductDao
import kotlinx.coroutines.delay

class MainPagingSource(private val dao: IProductDao) : PagingSource<Int,Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 0

        return try {
//            val entities = dao.getPagedList(params.loadSize,page*params.loadSize)

            if(page!=0) delay(1000)
                LoadResult.Error(throwable = IllegalArgumentException("Hello"))
//            LoadResult.Page(
//                data = entities,
//                prevKey = if (page == 0) null else page - 1,
//                nextKey = if (entities.isEmpty()) null else page + 1
//            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}