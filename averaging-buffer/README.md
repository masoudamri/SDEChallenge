1. AveragingNumberBuffer and AveragingNumberArrayBuffer are the interface implementation that solves the problem.

1. Implementation is thread safe, but might be more performant under a given workload if a read/write lock was used instead of synchronized blocks.

1. I'm doubtful equals/hashcode would work properly in AveragingArrayBuffer if buffers are defined via inline method references to provide metrics, although i haven't really tested it. Oh well.
