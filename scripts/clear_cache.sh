#!/bin/bash

# Set a default directory path
#directory_path="~/.m2/repository"
directory_path="$HOME/.m2/repository"

# Check if a command-line argument is provided
if [ "$#" -eq 1 ]; then
    directory_path="$1"
else
    echo "No directory path provided. Using default: $directory_path"
fi

cleanCache() {
    if [ ! -d "$directory_path" ]; then
        echo "Directory does not exist: $directory_path"
        exit 1
    fi

    # Use find to get the list of files to delete
    files_to_delete=($(find "$directory_path" -type f))

    if [ ${#files_to_delete[@]} -eq 0 ]; then
        echo "No files to delete in the directory: $directory_path"
        exit 0
    fi

    echo "Found the following files to delete:"
    for file in "${files_to_delete[@]}"; do
        echo "  - $file"
    done

    # Use find to delete all files in the directory
    find "$directory_path" -type f -delete

    echo "Deleted the following files:"
    for file in "${files_to_delete[@]}"; do
        echo "  - $file"
    done

    echo "Cache cleaning finished successfully."
}

cleanCache