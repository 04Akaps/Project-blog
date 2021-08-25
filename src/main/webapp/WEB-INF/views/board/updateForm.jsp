<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id}"/>

        <div class="form-group">
            <input value="${board.title}" type="text"  class="form-control" placeholder="Enter title" id="title">
        </div>

        <div class="form-group">
            <textarea value="${board.content}" class="form-control" rows="5" id="content"></textarea>
        </div>

    </form>

    <button id="btn-update" class="btn btn-primary">수정 완료</button>

</div>

<%@ include file="../layout/footer.jsp"%>

<script src="/js/board.js"></script>

