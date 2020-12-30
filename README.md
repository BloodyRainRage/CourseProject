Создать модель обслуживания потока заявок на разгрузку, поступающих от грузовых судов, прибывающих в морской порт.
<p>
Грузовые суда прибывают в порт согласно расписанию, но возможны опоздания и досрочные прибытия. Расписание включает
день и время прибытия, название судна, вид груза и его вес, а также планируемый срок стоянки в порту для разгрузки.
<p>
Для разгрузки судов в порту используются три вида разгрузочных кранов, соответствующих трем видам грузов: сыпучим и
жидким грузам, контейнерам. Число разгрузочных кранов каждого вида ограничено, так что поступающие заявки на разгрузку
одного вида груза образуют очередь. Длительность разгрузки судна зависит от вида и веса его груза, а также некоторых
других факторов, например, погодных условий. Любой дополнительный (сверх запланированного срока) день стояния судна
в порту (из-за ожидания разгрузки в очереди или из-за задержки самой разгрузки) влечет за собой выплату штрафа
1000 у. е. за каждый день простоя судна.
<p>
При моделировании прибытия судов отклонение их от расписания рассматривается как случайная величина с нормальным
распределением в интервале от -7 до 7 дней. Еще одной случайной величиной, изменяющейся в диапазоне от 0 до 10 дней,
является время задержки окончания разгрузки судна по сравнению с обычным (зависящим только от вида груза и его веса).
Цель моделирования работы морского порта – определение для заданного расписания прибытия судов минимально достаточного
числа кранов в порту, позволяющего уменьшить штрафные суммы. Период моделирования – 30 дней, шаг
моделирования – 1-3 дня. В параметры моделирования следует включить расписание прибытия судов, количество кранов
каждого вида, а также шаг моделирования.
<p>
В результате работы программы должен быть сформирован отчёт, содержащий: список произведенных разгрузок, в котором
указывается название разгруженного судна, время его прихода в порт и время ожидания в очереди на разгрузку, время
начала разгрузки и ее продолжительность, а также по окончании моделирования должна быть выведена итоговая статистика:
число разгруженных судов, средняя длина очереди на разгрузку, среднее время ожидания в очереди,
максимальная и средняя задержка разгрузки, общая сумма штрафа.