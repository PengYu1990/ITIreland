package com.hugo.itireland.service.impl;

import com.hugo.itireland.domain.Comment;
import com.hugo.itireland.repository.CommentRepository;
import com.hugo.itireland.service.CommentService;
import com.hugo.itireland.service.PostService;
import com.hugo.itireland.web.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
    @Override
    public Comment add(Comment comment) {
        comment.setCtime(LocalDateTime.now());
        comment.setUtime(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return commentRepository.findAllByState(pageable,0).toList();
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        if(comment == null) {
           throw new ApiRequestException("Comment doesn't exist!");
        }
        comment.setState(-1);
        commentRepository.save(comment);
    }
}
