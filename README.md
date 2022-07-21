# text-similarity-detector

### 计算中文内容重复值

主要采取LCS （最长公共字符串）进行比较，在比较段落 A 和 B的时候， A作为左侧参数，B作为右侧段落，那么A的重复度通过以下方式来算得:
 * 将段落A拆分成N个短句子， B拆分成M个短句子
 * 进N个句子中的每一个和B的M个句子比较， 找出公共字符串最长的那个 字符串
 * 将得到的公共字符串长度相加 除于 N个短句子的长度和，得到最后的重复度的分值。
 
 > 句子拆分和最长公共字符串都可以设置最小的阈值来过滤太短的字符串/句子。
