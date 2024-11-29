function cmn380LoadFile() {
  const cmn380ImageArea = document.getElementById('imgPreview');
  const viewer = new Viewer(cmn380ImageArea, {
    navbar: false,
    inline: true,
    title: false,
    toolbar: {
      zoomIn: 1,
      zoomOut: 1,
      reset: 1,
      rotateLeft: 1,
      rotateRight: 1,
      flipHorizontal: 1,
      flipVertical: 1
    },
    button: false,
    ready() {
      viewer.full();
    }
  });
}

function convertFile() {
  var extension = $('input[name="extension"]').val();
  if (extension == 'txt'
    || extension == 'js'
    || extension == 'css'
    || extension == 'html') {

    var url = $('input[name="url"]').val();

    //ダウンロードURLを実行し、ファイルを取得
    fetch(url)
      .then((response) => response.blob())
      .then((beforeConvertData) => {

        if (beforeConvertData) {

          var file = new File([beforeConvertData], "previewFile");

          var fd = new FormData();
          fd.append("CMD", "convertFile");
          fd.append("cmn380PreviewFileExtension", extension);
          fd.append("cmn380PreviewURL", url);
          fd.append("cmn380File", file);
          fd.append("cmn380SelectEncording", document.forms[0].cmn380SelectEncording.value);

          //ファイルの内容を無害化、ハイパーリンク化
          $.ajax({
            url: "../common/cmn380.do",
            type: "POST",
            processData: false,
            contentType: false,
            data: fd
          })
          .done(function(afterConvertData){
            if (afterConvertData["success"]) {
              if (extension == "html") {
                if ($('.htmlPreviewArea')) {
                  $('.htmlPreviewArea').remove();
                }
                $('#textPreviewArea').closest('div').attr('class', 'm0');
                const iframe = document.createElement('iframe');
                iframe.setAttribute('class', 'htmlPreviewArea w100 pos_abs border_none');
                iframe.srcdoc = afterConvertData["cmn380FileContent"];
                $('#textPreviewArea').closest('div').append(iframe);
                //プレビュー対象HTML内の内部リンクを無効化
                $('.htmlPreviewArea').on('load', function(){
                  $('.htmlPreviewArea').contents().find('a').each(function(index, element){
                    if ($(element).attr('href') != null
                      && ($(element).attr('href') == "" || $(element).attr('href').startsWith('#'))) {
                      $(element).css('pointer-events', 'none');
                    }
                  })
                });
              } else {
                $('#textPreviewArea').closest('div').attr('class', 'p10 mt0 mr10 mb40 ml10');
                $('#textPreviewArea').text("");
                $('#textPreviewArea').append(afterConvertData["cmn380FileContent"]);
              }
            }
          })
          .fail(function(afterConvertData){
            alert('通信に失敗しました。');
          });
        }
      });
  }
}