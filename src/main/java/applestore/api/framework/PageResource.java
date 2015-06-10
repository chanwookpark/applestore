package applestore.api.framework;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * @author chanwook
 */
public class PageResource<T> implements Serializable {
    private final Pageable pageRequest;
    private final T contents;

    public PageResource(T contents, Pageable pageRequest) {
        this.contents = contents;
        this.pageRequest = pageRequest;
    }

    public Pageable getPageRequest() {
        return pageRequest;
    }

    public T getContents() {
        return contents;
    }
}
