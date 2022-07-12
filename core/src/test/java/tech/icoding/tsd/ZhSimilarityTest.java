package tech.icoding.tsd;

import java.util.List;

/**
 * @author : Joe
 * @date : 2022/7/1
 */
public class ZhSimilarityTest {
    public static void main(String[] args) {

        String s = "针对低压电力线载波信道质量恶劣、主流厂商产品通信稳定性差等问题，本专利提出一个基于短时离散傅立叶变换的多路延时同步判决解调方法，优化提升了相关厂商载波模块现场运行抗干扰能力和通信组网性能，显著提高公司低压载波通信成功率，降低了采集运维成本。\n" +
                "    本专利通过将相干检测中同频的要求转化为离散傅里叶变换来获得近似频率，将同相的要求使用多路延时信号来判断近似最优值，解决了相干解调方式对频率同步及同相检测结果要求苛刻、易受载波初始相位偏移及载频估计误差影响等缺陷，具有与其它算法本质的区别和显著的性能优势。具体如下：\n" +
                "    1.将模拟域信号转化为数字域进行多路信号采样，便于软件实现和并行处理，提升了运算性能；\n" +
                "    2.利用短时傅里叶变换技术快速得到频率和相位同步信息，降低了信号解调延时和功耗，提升了通信成功率；\n" +
                "    3.信号解调不需要复杂的鉴频器、过零检测等器件，降低了硬件电路设计的复杂度和器件成本。 ";
        DefaultSentenceBreaker defaultSentenceBreaker = new DefaultSentenceBreaker();
        final List<String> strings = defaultSentenceBreaker.toSentenceList(s);
        strings.forEach(s1 -> {
            System.out.println(s1);
        });


        LcbSimilarityChecker paragraphSimilarityChecker = new LcbSimilarityChecker(new DefaultSentenceBreaker());

        String left = "计算段落之间的相似度，优先进行段落的拆分，将所有的段落拆分成一个一个的句子，然后进行N*M的比较。 N代表左边句子数量，M代表右边句子数量。\n" +
                " * 比较得到N*M的矩阵结果，针对左边每一句，取出最大分值作为汇总计算总的相似度分值。 总的相似度:（maxScore(N)/N）";

        String right = "计算段落之间的相似度，优先进行段落的拆分，将所有的段落拆分成一个一个的句子，然后进行N*M的比较。 N代表左边句子数量，M代表右边句子数量。\n" +
                " * 比较得到N*M的矩阵结果，针对左边每一句，取出最大分值作为汇总计算总的相似度分值。 总的相似度:（maxScore(N)/N）";

        final Float score = paragraphSimilarityChecker.apply(left, right);

        System.out.println(score);

    }
}
