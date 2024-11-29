package jp.groupsession.v2.wml.restapi.biz;

import java.io.IOException;
import java.util.List;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;

/**
 * <br>[機  能] WEBメールのRESTAPI 添付ファイル操作のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlRestTempFileBiz {

    /**
     * <br>[機  能] メール送信，草稿保存処理の中で追加した添付ファイルを削除する
     * <br>[解  説] 参照メール，テンプレートのファイルはAPI実行ごとに展開されるため、メールが送信できなかった場合に削除する必要がある
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param filePathList テンポラリディレクトリ内のファイル名リスト
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException ファイル操作時例外
     */
    public static void deleteTempFile(String tempDir, List<String> filePathList) throws IOException, IOToolsException {

        List<String> allFileName = IOTools.getFileNames(tempDir);
        allFileName.removeAll(filePathList);
        
        tempDir = IOTools.setEndPathChar(tempDir);
        for (String fileName : allFileName) {
            //ファイルの削除実行
            IOTools.deleteFile(tempDir + fileName);
        }
    }
}
