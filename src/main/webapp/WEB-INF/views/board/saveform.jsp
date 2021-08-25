<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <div class="form-group">
            <input type="text"  class="form-control" placeholder="Enter title" id="title">
        </div>

        <div class="form-group">
            <textarea class="form-control" rows="5" id="content"></textarea>
        </div>

    </form>

    <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>

</div>

<%@ include file="../layout/footer.jsp"%>

<script src="/js/board.js"></script>

