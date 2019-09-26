1. AveragingNumberBuffer and AveragingNumberArrayBuffer interface implementation that solves the problem.

1. Implementation is thread safe, but might be more performant if a read/write lock was used instead of synchronized blocks.

1. I'm doubtful equals/hashcode would work properly in super class if buffers are defined via inline method references for measures,although i haven't really tested it. Oh well.