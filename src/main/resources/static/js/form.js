const colors = ["#008FFB", "#00E396", "#775DD0", "#FEB019", "#FF4560"];
let processes = [];
let data = [];
let form = document.getElementById("simulation-form");
let chart = document.getElementById("simulation-chart");
let statsAvgWaitTime = document.getElementById("avg-wait-time");
let statsAvgTurnTime = document.getElementById("avg-turn-time");
let statsTotalTurnTime = document.getElementById("total-turn-time");

form.onsubmit = function (e) {
    e.preventDefault();

    reset();

    const algorithm = e.target.algorithm.value;
    runSimulation({"algorithm": algorithm})

    processes
        .forEach((process, index) => {
            process.runtime
                .forEach(runtime => {
                    data.push({
                        x: process.name,
                        y: [runtime.start, runtime.end],
                        fillColor: colors[index]
                    })
                })
        });

    showStats();
    chartData(data);
}

function showStats() {
    statsAvgWaitTime.innerHTML = processes
        .reduce((acc, curr) => acc + curr.waitingTime, 0) / processes.length;

    statsAvgTurnTime.innerHTML = processes
        .reduce((acc, curr) => acc + curr.turnAroundTime, 0) / processes.length;

    let maxCompletionTime = processes[0].completionTime;

    processes.forEach(proc => {
        if (proc.completionTime > maxCompletionTime)
            maxCompletionTime = proc.completionTime;
    });

    statsTotalTurnTime.innerHTML = maxCompletionTime;
}

function reset() {
    chart.innerHTML = "";
    statsAvgWaitTime.innerHTML = "";
    statsAvgTurnTime.innerHTML = "";
    statsTotalTurnTime.innerHTML = "";
    processes = [];
    data = [];
}

function chartData(data) {
    let options = {
        series: [{ data: data }],
        chart: {
            height: 350,
            type: 'rangeBar'
        },
        plotOptions: {
            bar: {
                horizontal: true,
                distributed: true,
                dataLabels: {
                    hideOverflowingLabels: false
                }
            }
        },
        dataLabels: {
            enabled: true,
            style: {
                colors: ['#f3f4f5', '#fff']
            }
        },
        tooltip:{
            enabled: true
        },
        xaxis: { type: 'numeric' },
        yaxis: {
            show: true
        },
        grid: {
            row: {
                colors: ['#f3f4f5', '#fff'],
                opacity: 1
            }
        }
    };

    let apexChart = new ApexCharts(document.querySelector("#simulation-chart"), options);
    apexChart.render();
}

function runSimulation(algorithm) {
    processes = $.ajax({
        async: false,
        type: "POST",
        url: "/simulate",
        data: JSON.stringify(algorithm),
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
    }).responseJSON;
}