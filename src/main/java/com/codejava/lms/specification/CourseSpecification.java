package com.codejava.lms.specification;

import com.codejava.lms.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

    public static Specification<Course> notDeleted() {
        return (root, query, cb) ->
                cb.isFalse(root.get("deleted"));
    }

    public static Specification<Course> hasTitle(String title) {
        return (root, query, cb) ->
                title == null ? null :
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + title.toLowerCase() + "%"
                        );
    }

    public static Specification<Course> hasCode(String code) {
        return (root, query, cb) ->
                code == null ? null :
                        cb.like(
                                cb.lower(root.get("code")),
                                "%" + code.toLowerCase() + "%"
                        );
    }
}