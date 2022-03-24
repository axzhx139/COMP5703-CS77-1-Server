package com.gday.trackmygrocery.service;


import com.gday.trackmygrocery.vo.params.FeedbackParam;

public interface FeedbackService {
    String storeFeedbackToTxt(FeedbackParam feedbackParam);
}
