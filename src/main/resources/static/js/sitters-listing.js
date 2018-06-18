$(document).ready(function() {
	
	var minRating = 1;
	// pageNum is zero-based
	var totalPages = 1;
	var currentPageNum = 0;
	var isFirst = false;
	var isLast = false;
	var numElementsOnPage = 0;

	getSitters(0);
	bindPageBtns();

	$(".rating-bar").attr("title", "click star to filter sitters based on rating");

	$(".rating-star").bind("click",
		function () {
			minRating = $(this).attr("id").slice(-1);
			updateRatingImg(minRating);
			getSitters(0);
		}
	);

	function getSitters(pageNum) {
		$.ajax({
			url:"http://localhost:8080/sitters?page=" + pageNum + "&minrating=" + minRating,
			type: "GET",
			dataType: "json",
			success: function(result) {				
				showResult(result);
				updateGlobalPageVars(result);
				updatePageBtns();
			}
		});
	}

	function updateRatingImg(id) {
		// update star images to reflect new rating input by user
		var i;
		for (i = 1; i <= id; i++) {
			var imgSrc = $("#star"+i).attr("src").replace("outline","solid");
			$("#star"+i).attr("src", imgSrc);
		}		
		for (i = ++id; i <= 5; i++) {
			var imgSrc = $("#star"+i).attr("src").replace("solid","outline");
			$("#star"+i).attr("src",imgSrc);
		}
	}

	function showResult(result) {

		var sitters = jQuery.parseJSON(JSON.stringify(result["content"]));
		var content = '<div class="row mt-5 justify-content-center">';
		var i;
		for (i = 0; i < sitters.length; i++) {
			content += '<div class="card mx-2 mb-3">' 
				 		+ '<img class="card-image-top" src="'
						+ sitters[i].imageUrl
						+ '">'
						+ '<div class="card-body">'
						+ '<h5 class="card-title">'
						+ sitters[i].name
						+ '</h5>'
						+ '<p class="card-text">'
						+ ratingToImg(sitters[i].avgRating)
						+ '</p>'
						+ '</div></div>';
		}
		content += '</div>';
		$("#query-result").html(content);
	}

	function ratingToImg(rating) {
		var ratingInt = parseInt(rating);
		var html = "";
		var i;
		for (i = 0; i < ratingInt; i++) {
			html += '<img class="sitter-rating-star" src="../img/star-solid-small.png"></img>';
		}
		return html;
	}

	function updateGlobalPageVars(result) {		
		totalPages = result["totalPages"];
		currentPageNum = result["number"];
	    isFirst = result["first"];
		isLast = result["last"];
		numElementsOnPage = result["numberOfElements"];
	}

	function updatePageBtns() {		
		if (totalPages == 1 || numElementsOnPage == 0) {
			$("#pagination-bar").hide();
		}
		if (isFirst) {
			$("#btn-previous-page").unbind("click");
			$("#btn-previous-page").addClass("not-bound")
			$("#btn-first-page").unbind("click");
			$("#btn-first-page").addClass("not-bound");
		} else {
			if ($("#btn-previous-page").hasClass("not-bound")) {
				bindPreviousPageBtn();
				$("#btn-previous-page").removeClass("not-bound");
			}
			if ($("#btn-first-page").hasClass("not-bound")) {
				bindFirstPageBtn();
				$("#btn-first-page").removeClass("not-bound");
			}
		}

		if (isLast) {
			$("#btn-next-page").unbind("click");
			$("#btn-next-page").addClass("not-bound");
			$("#btn-last-page").unbind("click");
			$("#btn-last-page").addClass("not-bound");
		} else {
			if ($("#btn-next-page").hasClass("not-bound")) {
				bindNextPageBtn();
				$("#btn-next-page").removeClass("not-bound");
			}
			if ($("#btn-last-page").hasClass("not-bound")) {
				bindLastPageBtn();
				$("#btn-last-page").removeClass("not-bound");
			}
		}
	
	}

	function bindFirstPageBtn() {
		$("#btn-first-page").bind("click", 
			function() {
				getSitters(0);
			}
		);
	}

	function bindPreviousPageBtn() {
		$("#btn-previous-page").bind("click", 
			function() {
				getSitters(currentPageNum-1);
			}
		);
	}

	function bindNextPageBtn() {
		$("#btn-next-page").bind("click", 
			function() {
				getSitters(currentPageNum+1);
			}
		);
	}

	function bindLastPageBtn() {
		$("#btn-last-page").bind("click", 
			function() {
				getSitters(totalPages-1);
			}
		);
	}

	function bindPageBtns() {
		bindFirstPageBtn();
		bindPreviousPageBtn();
		bindNextPageBtn();
		bindLastPageBtn();
	}
	
}); 
	
