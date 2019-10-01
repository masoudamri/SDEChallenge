1. included are System Context and Container diagrams as described by Simone Brown c4model.com
1. diagrams created in draw.io using Tobias Hochgürtel's plugin https://github.com/tobiashochguertel/c4-draw.io
1. diagram can be viewed by clicking [Here](https://www.draw.io/#Hmasoudamri%2FSDEChallenge%2Fsolution%2Fanalytics-arch%2Fanalytics-arch.drawio) or [Here](https://www.draw.io/#Uhttps%3A%2F%2Fraw.githubusercontent.com%2Fmasoudamri%2FSDEChallenge%2Fsolution%2Fanalytics-arch%2Fanalytics-arch.drawio) if you don't want to sign into github
1. above link depends on github repository and branch name!
1. reprocessing of informatioon can be achived manually by user via presentation web app or by inserting identifiers into appropriate queues
1. 'Peresistent Queue' is a distributed, high thoroughput, HA, durable, and allows for multiple consumers, and at least once semantics(eg Kafka)
1. queue written to by by edge service is durable, and requires no external dependencies, other processes read this queue by connecting to this container
1. 'timpestamp' always refers to time message was initially consumed by edge service, it is passed down, but not explicitly recorded in diagram
